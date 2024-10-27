package com.backend.booking.controller;

import com.backend.booking.service.StatisticsExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsExportService statisticsExportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/statistics/export")
    public void exportStatistics(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"statistics.csv\"");
        statisticsExportService.exportStatisticsToCSV(response.getWriter());
    }
}
