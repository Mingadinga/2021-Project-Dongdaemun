package com.dongdaemun.dongdaemun.web;

import com.dongdaemun.dongdaemun.service.posts.PostsService;
import com.dongdaemun.dongdaemun.web.dto.posts.PostsAndCommentsPageResponseDto;
import com.dongdaemun.dongdaemun.web.dto.posts.PostsAndCommentsResponseDto;
import com.dongdaemun.dongdaemun.web.dto.posts.PostsResponseDto;
import com.dongdaemun.dongdaemun.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    /* 게시글 수정 */
    // 조회와 같은 기능. 단, 댓글은 안가져오고 게시글만 가져옴
    // 프론트가 제이슨 데이터를 받아서 뿌려줘야함
    /*
    @GetMapping("/update/{category}/{id}")
    public ResponseEntity<PostsResponseDto> update(@PathVariable String category, @PathVariable Long id){
        return ResponseEntity.ok()
                .body(postsService.findById(id));
    }

     */

    /* 게시글 수정 */
    // 게시글 저장(savepost)와 같은 기능. 변경된 내용을 프론트가 받아서 전송하면
    // 그 내용을 db에 저장함
    @PutMapping("/update/{category}/{id}")
    public ResponseEntity<?> update(@PathVariable String category, @PathVariable Long id, @RequestBody PostsUpdateRequestDto updateRequestDto){
        return ResponseEntity.ok()
                .body(postsService.update(id, updateRequestDto));
    }

    /* 게시글 조회 */
    // 댓글 전체 조회
    @GetMapping("/view/{category}/{id}/scroll")
    public ResponseEntity<PostsAndCommentsResponseDto> postview(@PathVariable String category, @PathVariable Long id){
        return ResponseEntity.ok()
                .body(postsService.findPostAndCommentsById(id));
    }

    /* 게시글 조회 */
    // 댓글 페이징 조회
    @GetMapping("/view/{category}/{id}")
    public ResponseEntity<PostsAndCommentsPageResponseDto> postview(@PathVariable String category, @PathVariable Long id
    , @RequestParam(required = false, defaultValue = "0", value = "commentPage") int page){
        return ResponseEntity.ok()
                .body(postsService.findPostsAndCommentsWithPageById(id, page));
    }

    /* 게시판 목록 조회 */
    @GetMapping("/list/{category}")
    public ResponseEntity<?> list(Model model, @PathVariable String category, @RequestParam(required = false, defaultValue = "0", value = "page") int page){
        Page<?> listPage = null; int totalPage;
        postsService.list(category, page);

        totalPage = listPage.getTotalPages();

        model.addAttribute("posts", listPage.getContent());
        model.addAttribute("totalPage", totalPage);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /* 게시글 삭제 */
    @DeleteMapping("/delete/{category}/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @PathVariable String category){
        postsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}