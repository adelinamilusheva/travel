package bg.adi.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.adi.travel.dto.CommentDTO;
import bg.adi.travel.dto.ReadCommentDTO;
import bg.adi.travel.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Api(tags = "Comment Controller")
public class CommentController {
	private final CommentService commentService;

    @GetMapping("/{publicationId}")
    @ApiOperation(value = "Get all active comments by publication", notes = "Get all active comments by publication id")
    public List<ReadCommentDTO> getAllActiveCommentsByPublication(@PathVariable Integer publicationId) {
        return commentService.getAllActiveCommentsByPublication(publicationId);
    }
     
    @PostMapping("/create")
    @ApiOperation(value = "Create new comment", notes = "Create new comment")
    public ResponseEntity<Void> create(@RequestBody CommentDTO commentDTO) {
    	commentService.create(commentDTO);
        return ResponseEntity.status(201).build();
    }
    
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update comment", notes = "Update comment")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
    	commentService.update(id, commentDTO);
        return ResponseEntity.status(202).build();
    }
    
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete comment", notes = "Delete comment")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	commentService.delete(id);
        return ResponseEntity.status(204).build();
    }
}
