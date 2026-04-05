package com.lokesh.finflow.controller;

import com.lokesh.finflow.dto.request.RecordCreateRequest;
import com.lokesh.finflow.dto.response.RecordResponse;
import com.lokesh.finflow.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // Only Admins can create records
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RecordResponse> createRecord(@Valid @RequestBody RecordCreateRequest request) {
        return new ResponseEntity<>(recordService.createRecord(request), HttpStatus.CREATED);
    }

    // Admins, Analysts, and Viewers can all view records
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    @GetMapping
    public ResponseEntity<Page<RecordResponse>> getAllRecords(Pageable pageable) {
        return ResponseEntity.ok(recordService.getAllRecords(pageable));
    }

    // Only Admins can delete records
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
