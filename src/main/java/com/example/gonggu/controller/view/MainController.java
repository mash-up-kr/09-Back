package com.example.gonggu.controller.view;

import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.dto.view.HomeCardList;
import com.example.gonggu.dto.view.ItemCard;
import com.example.gonggu.service.view.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/*")
public class MainController {
    /*
    Home View 에 필요한 API
    @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
    */

    @Autowired
    MainService mainService;
    @Resource
    APIResponse returnResponse;

    @GetMapping("/home")
    public ResponseEntity<APIResponse<HomeCardList>> apiGetHome(){
        HttpStatus status = HttpStatus.OK;
        HomeCardList result = new HomeCardList();
//        List<ItemCard> returnJson = new ArrayList<>();

        // 최신글 + 핫아이템
        result.setPopularItemList(mainService.getPopularBoard());
        result.setRecentItemList(mainService.getRecentBoard(5));

        returnResponse.setStatus(status);
        returnResponse.setMessage("home view Info \n recent view + a hot item");
        returnResponse.setReturnJson(result);

        return new ResponseEntity<>(returnResponse, status);
    }

    @GetMapping("/home/allitem")
    public ResponseEntity<APIResponse> apiGetHomeMore(){
        HttpStatus status = HttpStatus.OK;
        Map<String,Object> returnJson = new HashMap<>();
        returnJson.put("recentAll",mainService.getRecentBoard(0));
        returnResponse.setStatus(status);
        returnResponse.setReturnJson(returnJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<APIResponse<List<ItemCard>>> apiGetCategoryItem(
            @PathVariable String categoryId
    ){
        HttpStatus status = HttpStatus.OK;

        returnResponse.setStatus(status);
        returnResponse.setMessage("return Category Item");
        returnResponse.setReturnJson(mainService.apiGetCategoryItem(Long.valueOf(categoryId)));

        return new ResponseEntity<>(returnResponse, status);
    }


}
