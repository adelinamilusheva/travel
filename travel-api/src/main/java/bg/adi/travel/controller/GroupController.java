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

import bg.adi.travel.dto.GroupDTO;
import bg.adi.travel.dto.ListGroupDTO;
import bg.adi.travel.dto.LitePage;
import bg.adi.travel.dto.MenuGroupDTO;
import bg.adi.travel.dto.ShortGroupDTO;
import bg.adi.travel.service.GroupService;
import bg.adi.travel.util.SortingUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    
    @GetMapping("/paging")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Get all paged groups", notes = "Get all paged groups")
    public LitePage<ListGroupDTO> getAllGroupsPage(
    		   @RequestParam(defaultValue = "0") int page,
    	       @RequestParam(defaultValue = "10") int size,
    	       @RequestParam(defaultValue = "id", required = false) String sortBy,
    	       @RequestParam(defaultValue = "asc", required = false) String sortDirection
    ) {
    	Sort.Direction evalDirection = SortingUtil.replaceOrderStringThroughDirection(sortDirection);
    	Sort sortOrderIgnoreCase = Sort.by(new Sort.Order(evalDirection, sortBy).ignoreCase());

    	return groupService.findAllPageable(PageRequest.of(page, size, sortOrderIgnoreCase));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get group by id", notes = "Get group by id")
    public GroupDTO getById(@PathVariable Integer id) {
        return groupService.getById(id);
    }

    @GetMapping("/menu")
    @ApiOperation(value = "Get all menu groups", notes = "Get all menu groups")
    public List<MenuGroupDTO> getMenuGroups() {
        return groupService.getMenuGroups();
    }
    
    @GetMapping("/all")
    @ApiOperation(value = "Get all groups", notes = "Get all groups")
    public List<ListGroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }
    
    @GetMapping("/all-short")
    @ApiOperation(value = "Get all short groups", notes = "Get all short groups")
    public List<ShortGroupDTO> getAllShortGroups() {
        return groupService.getAllShortGroups();
    }
    
    @GetMapping("/all-valid")
    @ApiOperation(value = "Get all valid groups", notes = "Get all valid groups")
    public List<GroupDTO> getAllValidGroups() {
        return groupService.getAllValidGroups();
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Create group", notes = "Create group")
    public ResponseEntity<Void> create(@RequestBody GroupDTO groupDTO) {
    	groupService.create(groupDTO);
        return ResponseEntity.status(201).build();
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update group", notes = "Update group")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody GroupDTO groupDTO) {
    	groupService.update(id, groupDTO);
        return ResponseEntity.status(202).build();
    }
    
    @PatchMapping("/status/{id}/{isActive}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update isActive", notes = "Update active/unactive group status")
    public ResponseEntity<Void> updateIsActive(@PathVariable Integer id, @PathVariable Boolean isActive) {
    	groupService.updateIsActive(id, isActive);
        return ResponseEntity.status(202).build();
    }
}
