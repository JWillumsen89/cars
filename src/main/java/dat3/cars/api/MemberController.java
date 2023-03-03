package dat3.cars.api;


import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/members")
public class MemberController {

  MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  //ADMIN ONLY
  @GetMapping
  List<MemberResponse> getMembers(){
    return memberService.getMembers(true);
  }

  //ADMIN
  @GetMapping(path = "/{username}")
  MemberResponse getMemberById(@PathVariable String username) throws Exception {
    return memberService.getMemberByUsername(username, true);
  }

  @GetMapping("/fn/{firstName}")
  List<MemberResponse> getMembersByFirstName(@PathVariable String firstName) throws Exception {
    return memberService.getMembersByFirstName(firstName, true);
  }

  //ANONYMOUS
  //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping
  MemberResponse addMember(@RequestBody MemberRequest body){
    return memberService.addMember(body);
  }

  //MEMBER
  @PutMapping("/{username}")
  ResponseEntity<Boolean> editMember(@RequestBody MemberRequest body, @PathVariable String username){
    return memberService.updateMemberDetails(body, username);
  }

  //ADMIN
  @PatchMapping("/ranking/{username}/{value}")
  void setRankingForUser(@PathVariable String username, @PathVariable int value) {
    memberService.setRankingValue(username, value);
  }

  // ADMIN
  @DeleteMapping("/{username}")
  void deleteMemberByUsername(@PathVariable String username) {
    memberService.deleteByUsername(username);
  }



}







