package com.uninter.vidaplus.infra.rule;

import com.uninter.vidaplus.infra.rule.dto.InputBaseDto;
import com.uninter.vidaplus.infra.rule.dto.OutputBaseDto;

public abstract class BaseRule {
    public abstract OutputBaseDto execute(InputBaseDto inputBaseDto);
}
