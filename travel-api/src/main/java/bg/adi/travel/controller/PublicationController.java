package bg.adi.travel.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.adi.travel.dto.ListPublicationDTO;
import bg.adi.travel.dto.LitePage;
import bg.adi.travel.dto.ManipulatePublicationDTO;
import bg.adi.travel.dto.PublicationDTO;
import bg.adi.travel.dto.ReadingStatsDTO;
import bg.adi.travel.dto.ShortPublicationDTO;
import bg.adi.travel.service.PublicationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import bg.adi.travel.util.SortingUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publications")
public class PublicationController {
    private final PublicationService publicationService;
    
    @GetMapping("/paging")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Get all paged publications", notes = "Get all paged publications")
    public LitePage<ListPublicationDTO> getAllPublicationsPage(
    		   @RequestParam(defaultValue = "0") int page,
    	       @RequestParam(defaultValue = "10") int size,
    	       @RequestParam(defaultValue = "id", required = false) String sortBy,
    	       @RequestParam(defaultValue = "asc", required = false) String sortDirection
    ) {
    	Sort.Direction evalDirection = SortingUtil.replaceOrderStringThroughDirection(sortDirection);
    	Sort sortOrderIgnoreCase = Sort.by(new Sort.Order(evalDirection, sortBy).ignoreCase());

    	return publicationService.findAllPageable(PageRequest.of(page, size, sortOrderIgnoreCase));
    }
    
    @GetMapping("/group/{groupId}/paging")
    @ApiOperation(value = "Get all paged publications", notes = "Get all paged publications")
    public LitePage<ShortPublicationDTO> getAllPublicationsByGroupPage(
    		   @PathVariable Integer groupId,
    		   @RequestParam(defaultValue = "0") int page,
    	       @RequestParam(defaultValue = "10") int size
    ) {
    	return publicationService.findAllPageableByGroup(groupId, PageRequest.of(page, size));
    }
    
    @GetMapping("/tag/{tagId}/paging")
    @ApiOperation(value = "Get all paged publications", notes = "Get all paged publications")
    public LitePage<ShortPublicationDTO> getAllPublicationsByTagPage(
    		   @PathVariable Integer tagId,
    		   @RequestParam(defaultValue = "0") int page,
    	       @RequestParam(defaultValue = "10") int size
    ) {
    	return publicationService.findAllPageableByTag(tagId, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get publication by id", notes = "Get publication by id")
    public PublicationDTO getById(@PathVariable Integer id) {
        return publicationService.getById(id);
    }

    @GetMapping("/{id}/readings")
    @ApiOperation(value = "Get readings by publication", notes = "Get readings by publication id")
    public ReadingStatsDTO getReadings(@PathVariable Integer id) {
        return publicationService.getReadings(id);
    }

    @GetMapping("/{id}/similar")
    @ApiOperation(value = "Get similar publications", notes = "Get similar publications by id")
    public List<ShortPublicationDTO> getSimilarById(@PathVariable Integer id) {
        return publicationService.getSimilarById(id);
    }

    @GetMapping("/latest")
    @ApiOperation(value = "Get latest publications by given size", notes = "Get latest publications by given size")
    public List<ShortPublicationDTO> getLatest(@RequestParam(defaultValue = "5", required = false) Integer size) {
        return publicationService.getLatest(size);
    }

    @GetMapping("/group/{groupId}")
    @ApiOperation(value = "Get publications by group", notes = "Get publications by group id")
    public List<ShortPublicationDTO> getByGroupId(@PathVariable Integer groupId) {
        return publicationService.getByGroupId(groupId);
    }

    @GetMapping("/tag/{tagId}")
    @ApiOperation(value = "Get publications by tag", notes = "Get publications by tag id")
    public List<ShortPublicationDTO> getByTagId(@PathVariable Integer tagId) {
        return publicationService.getByTagId(tagId);
    }
    
    @GetMapping("/all")
    @ApiOperation(value = "Get all publications", notes = "Get all publications")
    public List<ListPublicationDTO> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Create publication", notes = "Create publication")
    public ResponseEntity<Void> create(@RequestBody ManipulatePublicationDTO publication) {
        publicationService.create(publication);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/{id}/add-reading")
    @ApiOperation(value = "Create reading by publication id", notes = "Create reading by publication id")
    public ResponseEntity<Void> create(@PathVariable Integer id, @RequestBody String ip) {
        publicationService.addReading(id, ip);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update publication", notes = "Update publication")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ManipulatePublicationDTO publication) {
        publicationService.update(id, publication);
        return ResponseEntity.status(202).build();
    }
    
    @PatchMapping("/status/{id}/{isActive}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update isActive", notes = "Update active/unactive publication status")
    public ResponseEntity<Void> updateIsActive(@PathVariable Integer id, @PathVariable Boolean isActive) {
    	publicationService.updateIsActive(id, isActive);
        return ResponseEntity.status(202).build();
    }
}
