package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;

public class SkillGemTypePart extends BlueprintPart<SkillGem, SkillGemBlueprint> {

    public SkillGemTypePart(SkillGemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected SkillGem generateIfNull() {
        return Database.SkillGems()
            .random();
    }
}
