package com.tinasheGomo.EventManagementSystem.service.counter;

import com.tinasheGomo.EventManagementSystem.entity.counter.CounterEntity;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.BusinessRuleException;
import com.tinasheGomo.EventManagementSystem.repository.counter.CounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;

    @Transactional
    public String getNextNumber(String organizationId, String prefix) {
        int year = Year.now().getValue();
        String id = organizationId + "_" + prefix + "_" + year;

        CounterEntity counter = counterRepository.findById(id)
                .orElseGet(() -> {
                    CounterEntity c = new CounterEntity();
                    c.setId(id);
                    c.setValue(0);
                    c.setPrefix(prefix);
                    c.setOrganizationId(organizationId);
                    c.setYear(year);
                    return c;
                });

        int next = counter.getValue() + 1;
        if (next > 9999) {
            throw new BusinessRuleException("Number sequence exhausted for " + prefix + " in " + year);
        }

        counter.setValue(next);
        counterRepository.save(counter);

        return prefix + "-" + year + "-" + String.format("%04d", next);
    }
}
