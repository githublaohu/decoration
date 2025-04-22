/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.decoration.core.spring.validation.validato;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author laohu
 */
public class ValidEnumValidator implements ConstraintValidator<ValidEnumRange, Object> {

    private ValidEnumRange range;

    private Set<Object> valueSet;

    private boolean contains;

    @Override
    public void initialize(ValidEnumRange constraintAnnotation) {
        this.range = constraintAnnotation;
        this.contains = constraintAnnotation.contains();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (Objects.isNull(valueSet)) {
            synchronized (this) {
                if (Objects.isNull(valueSet)) {
                    valueSet = new HashSet<>();
                    Class<Enum> clazz = (Class<Enum>) value.getClass();
                    for (String e : range.enums()) {
                        valueSet.add(Enum.valueOf(clazz, e));
                    }
                }
            }
        }

        return contains == valueSet.contains(value);
    }
}
