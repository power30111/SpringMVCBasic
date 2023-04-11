package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component

//검증 해야 하는 부분을 한곳에 모아서 분리시켜서 Controller 부분 코드를 간결하게 볼수있게하기 위해서 검증기(Validator)개념을 도입 -> V5
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //item == clazz     파라미터로 넘어오는 클래스가 Item 타입이 지원이 되나?
        //item == subItem   자식 클래스인가?
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        if (!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price","range",new Object[]{1000,1000000},null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.rejectValue("quantity","max",new Object[]{9999},null);
        }
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                errors.reject("totalPriceMin",new Object[]{10000,result},null);
            }
        }
    }
}
