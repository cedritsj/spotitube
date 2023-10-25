package com.example.spotitube.spotitubeapp.resources.dto;

import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

public class DTOTest {
    @Test
    public void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate("com.example.spotitube.spotitubeapp.resources.dto", new FilterPackageInfo());
        validator.validate("com.example.spotitube.spotitubeapp.resources.dto.request", new FilterPackageInfo());
        validator.validate("com.example.spotitube.spotitubeapp.resources.dto.response", new FilterPackageInfo());
    }
}
