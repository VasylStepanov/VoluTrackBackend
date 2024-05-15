CREATE TABLE IF NOT EXISTS group_data.members(
    id UUID CONSTRAINT members_id_pk PRIMARY KEY,
    volunteer_id UUID NOT NULL,
    group_id UUID NOT NULL,
    member_role SMALLINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_volunteer_and_group UNIQUE(volunteer_id, group_id),
    CONSTRAINT fk_member_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteers(id) ON DELETE CASCADE,
    CONSTRAINT fk_member_group_id FOREIGN KEY(group_id) REFERENCES group_data.groups(id) ON DELETE CASCADE
);