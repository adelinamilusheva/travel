package bg.adi.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Group;
import bg.adi.travel.data.repository.pub.GroupRepository;
import bg.adi.travel.data.specification.GroupSpecifications;
import bg.adi.travel.dto.GroupDTO;
import bg.adi.travel.dto.ListGroupDTO;
import bg.adi.travel.dto.LitePage;
import bg.adi.travel.dto.MenuGroupDTO;
import bg.adi.travel.dto.ShortGroupDTO;
import bg.adi.travel.service.mapper.GroupMapper;
import bg.adi.travel.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupDTO getById(Integer id) {
        Optional<Group> group = groupRepository.findById(id);
        ValidationUtil.validate(group);

        return GroupMapper.toGroupDTO(group.get());
    }

    public List<MenuGroupDTO> getMenuGroups() {
        List<Group> groups = groupRepository.findAll(GroupSpecifications.isActiveTopLevelGroup());

        return groups.stream().map (g -> GroupMapper.toMenuGroupDTO(g) ).collect(Collectors.toList());
    }
    
    public List<ListGroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAllByOrderByIdDesc();

        return groups.stream().map (g -> GroupMapper.toListGroupDTO(g)).collect(Collectors.toList());
    }
    
    public List<ShortGroupDTO> getAllShortGroups() {
    	List<Group> groups = groupRepository.findAll();
    	
    	return groups.stream().map (g -> GroupMapper.toShortGroupDTO(g)).collect(Collectors.toList());
    }
    
    public List<GroupDTO> getAllValidGroups() {
        List<Group> groups = groupRepository.findAllByIsActive(true);

        return groups.stream().map (g -> GroupMapper.toGroupDTO(g)).collect(Collectors.toList());
    }
    
    @Transactional
    public void create(GroupDTO group) {
    	groupRepository.save(
                Group.builder()
                   .name(group.getName())
                   .description(group.getDescription())
                   .parent(GroupMapper.toIdOnlyEntity(group.getParent().getId()))
                   .build()
        );
    }
    
    @Transactional
    public void update(Integer id, GroupDTO updatedGroup) {
    	Optional<Group> group = groupRepository.findById(id);
        ValidationUtil.validate(group);

        group.get().setName(updatedGroup.getName());
        group.get().setDescription(updatedGroup.getDescription());
        group.get().setParent(GroupMapper.toIdOnlyEntity(updatedGroup.getParent().getId()));
    }
    
    @Transactional
    public void updateIsActive(Integer id, Boolean isActive) {
    	Optional<Group> group = groupRepository.findById(id);
        ValidationUtil.validate(group);

        group.get().setIsActive(isActive);
    }
    
    public LitePage<ListGroupDTO> findAllPageable(PageRequest pageRequest) {
		Page<Group> groups = groupRepository.findAll(pageRequest);

		return LitePage.<ListGroupDTO>builder()
				.pageIndex(groups.getNumber())
				.pageSize(groups.getSize())
				.totalElements(groups.getTotalElements())
				.results(
						groups.getContent().stream().map (g -> GroupMapper.toListGroupDTO(g)).collect(Collectors.toList())
				)
				.build();
	}
}
