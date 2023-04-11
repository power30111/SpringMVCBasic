package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;


    //Controller 호출될때 검증기 적용
    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과를 보관하는 객체 -> bindingResult
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수입니다. "));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다. "));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item","quantity","상품 수량은 9999개 까지 등록 가능합니다. "));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합 이 10,000 을 넘어야 합니다. "));
            }
        }
        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
            //bindingresult는 자동으로 modelAttribute 에 들어간다.
        }
        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과를 보관하는 객체 -> bindingResult
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){
            //bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수입니다. "));
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품 이름은 필수입니다. "));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            //bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다. "));
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000 ~ 1,000,000 까지 허용합니다. "));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            //bindingResult.addError(new FieldError("item","quantity","상품 수량은 9999개 까지 등록 가능합니다. "));
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"상품 수량은 9999개 까지 등록 가능합니다. "));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                //bindingResult.addError(new ObjectError("item","가격 * 수량의 합 이 10,000 을 넘어야 합니다. "));
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합 이 10,000 을 넘어야 합니다. "));
            }
        }
        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
            //binding result는 자동으로 modelAttribute 에 들어간다.
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target ={}",bindingResult.getTarget());

        //검증 오류 결과를 보관하는 객체 -> bindingResult
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},null));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,result},null));
            }
        }
        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //@PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //간단하게 값이 안들어갔거나 스페이스바로 값을 채워넣었을경우 바로 아래 if문 처럼 동작한다. 단, 단순한 기능들만 제공
        //ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required");

        if (!StringUtils.hasText(item.getItemName())){
            //bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null));
            bindingResult.rejectValue("itemName","required");
            ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            //bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
            bindingResult.rejectValue("price","range",new Object[]{1000,1000000},null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            //bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},null));
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null);
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
//                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,result},null));
                bindingResult.reject("totalPriceMin",new Object[]{10000,result},null);
            }
        }
        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //@PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        itemValidator.validate(item,bindingResult); //검증기 개념 도입

        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    @PostMapping("/add")        //@Validated 가 있으면 해당 객체에 대해서 자동으로 검증기 적용(@InitBinder,@WebDataBinder 와 관련)
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

