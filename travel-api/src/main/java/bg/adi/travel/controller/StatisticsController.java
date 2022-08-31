package bg.adi.travel.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.adi.travel.dto.StatisticsGroupDTO;
import bg.adi.travel.dto.StatisticsPublicationDTO;
import bg.adi.travel.dto.StatisticsTagDTO;
import bg.adi.travel.service.StatisticsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
	 private final StatisticsService statisticsService;
	 
	 @GetMapping("/tags")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @ApiOperation(value = "Get all tags for statistics", notes = "Get all tags for statistics")
	 public StatisticsTagDTO getStatisticsTags() {
	      return statisticsService.getAllStatisticsTags();
	 }
	 
	 @GetMapping("/publications")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @ApiOperation(value = "Get all publications for statistics", notes = "Get all publications for statistics")
	 public StatisticsPublicationDTO getStatisticsPublications() {
	      return statisticsService.getAllStatisticsPublications();
	 }
	 
	 @GetMapping("/groups")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @ApiOperation(value = "Get all groups for statistics", notes = "Get all groups for statistics")
	 public StatisticsGroupDTO getStatisticsGroups() {
	      return statisticsService.getAllStatisticsGroups();
	 }
}
