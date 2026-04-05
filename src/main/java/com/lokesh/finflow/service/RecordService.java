package com.lokesh.finflow.service;

import com.lokesh.finflow.dto.request.RecordCreateRequest;
import com.lokesh.finflow.dto.response.RecordResponse;
import com.lokesh.finflow.exception.ResourceNotFoundException;
import com.lokesh.finflow.model.FinancialRecord;
import com.lokesh.finflow.model.User;
import com.lokesh.finflow.model.UserPrincipal;
import com.lokesh.finflow.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final FinancialRecordRepository recordRepository;

    public RecordResponse createRecord(RecordCreateRequest request) {
        User currentUser = getCurrentUser();

        FinancialRecord record = FinancialRecord.builder()
                .amount(request.amount())
                .type(request.type())
                .category(request.category())
                .date(request.date())
                .description(request.description())
                .user(currentUser)
                .build();

        FinancialRecord savedRecord = recordRepository.save(record);
        return mapToResponse(savedRecord);
    }

    public Page<RecordResponse> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable).map(this::mapToResponse);
    }

    public void deleteRecord(Long id) {
        if(!recordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Record not found with id: " + id);
        }
        recordRepository.deleteById(id);
    }

    private User getCurrentUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

    private RecordResponse mapToResponse(FinancialRecord savedRecord) {
        return new RecordResponse(
                savedRecord.getId(),
                savedRecord.getAmount(),
                savedRecord.getType(),
                savedRecord.getCategory(),
                savedRecord.getDate(),
                savedRecord.getDescription(),
                savedRecord.getUser().getEmail()
        );
    }
}
