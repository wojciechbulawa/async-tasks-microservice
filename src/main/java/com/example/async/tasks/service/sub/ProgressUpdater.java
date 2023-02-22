package com.example.async.tasks.service.sub;

import java.math.BigDecimal;

public interface ProgressUpdater {

    void addProgressPercentage(BigDecimal percentage);
}
