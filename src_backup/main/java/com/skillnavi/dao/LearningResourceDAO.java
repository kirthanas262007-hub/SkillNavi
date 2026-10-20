package com.skillnavi.dao;

import com.skillnavi.model.LearningResource;
import java.util.Collections;
import java.util.List;

public class LearningResourceDAO {

    public List<LearningResource> findBySkillId(int skillId) {
        // Learning resources are not yet stored in the database.
        return Collections.emptyList();
    }
}