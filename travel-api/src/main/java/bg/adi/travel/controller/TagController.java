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

import bg.adi.travel.dto.ListTagDTO;
import bg.adi.travel.dto.LitePage;
import bg.adi.travel.dto.ShortTagDTO;
import bg.adi.travel.service.TagService;
import bg.adi.travel.util.SortingUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get tag by id", notes = "Get tag by id")
    public ShortTagDTO getById(@PathVariable Integer id) {
        return tagService.getById(id);
    }
    
    @GetMapping("/paging")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Get all paged tags", notes = "Get all paged tags")
    public LitePage<ListTagDTO> getAllTagsPage(
    		   @RequestParam(defaultValue = "0") int page,
    	       @RequestParam(defaultValue = "10") int size,
    	       @RequestParam(defaultValue = "id", required = false) String sortBy,
    	       @RequestParam(defaultValue = "asc", required = false) String sortDirection
    ) {
    	Sort.Direction evalDirection = SortingUtil.replaceOrderStringThroughDirection(sortDirection);
    	Sort sortOrderIgnoreCase = Sort.by(new Sort.Order(evalDirection, sortBy).ignoreCase());
    	
    	return tagService.findAllPageable(PageRequest.of(page, size, sortOrderIgnoreCase));
    }
    
    @GetMapping("/all")
    @ApiOperation(value = "Get all tags", notes = "Get all tags")
    public List<ListTagDTO> getAllTags() {
        return tagService.getAllTags();
    }
    
    @GetMapping("/all-valid")
    @ApiOperation(value = "Get all valid tags", notes = "Get all valid tags")
    public List<ShortTagDTO> getAllValidTags() {
        return tagService.getAllValidTags();
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Create tag", notes = "Create tag")
    public ResponseEntity<Void> create(@RequestBody ShortTagDTO tag) {
    	tagService.create(tag);
        return ResponseEntity.status(201).build();
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update tag", notes = "Update tag by tag id")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ShortTagDTO tag) {
    	tagService.update(id, tag);
        return ResponseEntity.status(202).build();
    }
    
    @PatchMapping("/status/{id}/{isActive}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update isActive", notes = "Update active/unactive tag status")
    public ResponseEntity<Void> updateIsActive(@PathVariable Integer id, @PathVariable Boolean isActive) {
    	tagService.updateIsActive(id, isActive);
        return ResponseEntity.status(202).build();
    }
}
