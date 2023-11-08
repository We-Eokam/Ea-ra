package com.eokam.groo.global.validation;

import com.eokam.groo.global.validation.ValidationGroups.NotNullGroup;
import com.eokam.groo.global.validation.ValidationGroups.OtherCheckGroup;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, NotNullGroup.class, OtherCheckGroup.class})
public interface ValidationSequence {
}
