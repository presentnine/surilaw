package ga.surilaw.controller.bookmark;

import ga.surilaw.domain.dto.AddPrecedentDto;
import ga.surilaw.domain.dto.BookmarkListRequestDto;
import ga.surilaw.domain.dto.CreateBookmarkDto;
import ga.surilaw.domain.entity.BookmarkedPrecedent;
import ga.surilaw.service.bookmark.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkController {

    BookmarkService bookmarkService;

    @PostMapping("/api/bookmark")
    public void createBookmark(@RequestBody CreateBookmarkDto createBookmarkDto){
        bookmarkService.createBookmark(createBookmarkDto.getBookmarkName(), createBookmarkDto.getUserId());
    }

    @PostMapping("/api/bookmark/{bookmarkId}")
    public void addPrecedentToBookmark(@PathVariable Long bookmarkId, @RequestBody AddPrecedentDto addPrecedentDto){
        bookmarkService.addPrecedentToBookmark(bookmarkId, addPrecedentDto.getPrecedentNum(), addPrecedentDto.getPrecedentAlias());
    }

    @GetMapping("/api/bookmark")
    public ResponseEntity<?> getAllBookmarkOfMember(@RequestBody BookmarkListRequestDto bookmarkListRequestDto){
        return ResponseEntity.ok(bookmarkService.getAllBookmarkOfMember(bookmarkListRequestDto.getUserId()));
    }

    @GetMapping("/api/bookmark/{bookmarkId}")
    public ResponseEntity<?> getBookmarkById(@PathVariable Long bookmarkId){
        return ResponseEntity.ok(bookmarkService.getBookmarkById(bookmarkId));
    }

    @PutMapping("/api/bookmark/{bookmarkId}")
    public void changeBookmarkName(@PathVariable Long bookmarkId, @RequestParam String bookmarkName){
        bookmarkService.changeBookmarkName(bookmarkId, bookmarkName);
    }

    @PutMapping("/api/bookmark/{bookmarkId}/{precedentId}")
    public void changePrecedentAlias(@PathVariable Long bookmarkId, @PathVariable int precedentId, @RequestParam String precedentAlias){
        bookmarkService.changePrecedentAlias(bookmarkId, precedentId, precedentAlias);
    }

    @DeleteMapping("/api/bookmark/{bookmarkId}")
    public void deleteBookmark(@PathVariable Long bookmarkId){
        bookmarkService.deleteBookmark(bookmarkId);
    }

    @DeleteMapping("/api/bookmark/{bookmarkId}/{precedentId}")
    public void deleteBookmarkedPrecedent(@PathVariable Long bookmarkId, @PathVariable int precedentId){
        bookmarkService.deleteBookmarkedPrecedent(bookmarkId, precedentId);
    }
}
