package com.application.content.items.request.service.impl;

import com.application.content.general.address.model.Address;
import com.application.content.items.item.model.ItemType;
import com.application.content.items.request.model.RequestStatus;
import com.application.content.items.request.dto.ResponseRequestItemDto;
import com.application.content.items.item.service.ItemValidation;
import com.application.content.items.request.model.Request;
import com.application.content.items.request.repository.RequestItemRepository;
import com.application.content.items.request.repository.RequestRepository;
import com.application.content.items.request.service.RequestService;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.request.dto.RequestItemDto;
import com.application.content.items.request.model.RequestItem;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    @Setter
    @Value("${const.size_length}")
    double SIZE_LENGTH;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestItemRepository requestItemRepository;

    @Autowired
    ItemValidation itemValidation;

    @Override
    public List<RequestItem> findAllRequestItemsByAddress(Address address) {
        return requestItemRepository.findAllByAddress(address.getCoordinatesLongitude() + SIZE_LENGTH,
                address.getCoordinatesLongitude() - SIZE_LENGTH,
                address.getCoordinatesLatitude() + SIZE_LENGTH,
                address.getCoordinatesLatitude() - SIZE_LENGTH);
    }

    @Override
    public List<RequestItem> findAllRequestItemsByAddressAndItemType(Address address, ItemType itemType){
        return requestItemRepository.findAllByAddress(address.getCoordinatesLongitude() + SIZE_LENGTH,
                address.getCoordinatesLongitude() - SIZE_LENGTH,
                address.getCoordinatesLatitude() + SIZE_LENGTH,
                address.getCoordinatesLatitude() - SIZE_LENGTH,
                itemType);
    }

    @Override
    public Volunteer getRepresentative(RequestItem requestItem){
        if(requestItem.getRequest().getGroup() == null)
            return requestItem.getRequest().getVolunteer();
        return requestItem.getRequest().getGroup().getVolunteer();
    }

    @Override
    public List<ResponseRequestItemDto> findAllRequestItems(UUID volunteerId, UUID groupId) {
        Request request = getRequest(volunteerId, groupId);
        return requestItemRepository
                .findAllByRequestId(request.getId())
                .stream()
                .map(ResponseRequestItemDto::toResponseRequestItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestItem saveRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId) {
        Request request = getRequest(volunteerId, groupId);
        RequestItem requestItem = RequestItem.builder()
                .requestStatus(RequestStatus.CREATED)
                .endProduct(requestItemDto.endProduct())
                .amount(itemValidation.eitherMoreThanZeroEqualFull(requestItemDto.amount()))
                .weight(itemValidation.eitherMoreThanZeroEqualFull(requestItemDto.weight()))
                .itemType(requestItemDto.itemType())
                .request(request)
                .build();
        requestItemRepository.save(requestItem);
        return requestItem;
    }

    @Override
    public void updateRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID requestItemId) {
        Request request = getRequest(volunteerId, groupId);
        RequestItem requestItem = request.getRequestItems().stream()
                .filter(x -> x.getId().equals(requestItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("RequestItem isn't from the requests"));
        if(requestItem.getRequestStatus().equals(RequestStatus.IN_PROCESS))
            throw new RuntimeException("Can't update request is in process");
        if(requestItem.getRequestStatus().equals(RequestStatus.COMPLETED))
            throw new RuntimeException("Can't update request is completed");
        if(requestItemDto.endProduct() != null)
            requestItem.setEndProduct(requestItemDto.endProduct());
        if(requestItemDto.amount() != null)
            requestItem.setAmount(itemValidation.eitherMoreThanZeroEqual(requestItemDto.amount()));
        if(requestItemDto.weight() != null)
            requestItem.setWeight(itemValidation.eitherMoreThanZeroEqual(requestItemDto.weight()));
        if(requestItemDto.itemType() != null)
            requestItem.setItemType(requestItemDto.itemType());
    }

    @Override
    public void deleteRequestItem(UUID volunteerId, UUID groupId, UUID requestItemId) {
        Request request = getRequest(volunteerId, groupId);
        RequestItem requestItem = request.getRequestItems().stream()
                .filter(x -> x.getId().equals(requestItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("RequestItem isn't from the requests"));
        if(requestItem.getRequestStatus().equals(RequestStatus.IN_PROCESS))
            throw new RuntimeException("Can't delete request is in process");
        requestItemRepository.delete(requestItem);
    }

    @Override
    public void deleteRequestById(UUID requestId) {
        requestRepository.deleteById(requestId);
    }

    @SneakyThrows
    private Request getRequest(UUID volunteerId, UUID groupId) {
        if(groupId == null)
            return getVolunteerRequest(volunteerId);
        return getGroupRequest(volunteerId, groupId);
    }

    @SneakyThrows
    private Request getVolunteerRequest(UUID volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        return volunteer.getRequest();
    }

    @SneakyThrows
    private Request getGroupRequest(UUID volunteerId, UUID groupId) {
        Group group = groupService.eitherIsAGroupModerator(volunteerId, groupId);
        return group.getRequest();
    }
}
