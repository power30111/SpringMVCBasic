package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

//    @NotNull(groups = updateCheck.class)
    private Long id;

//    @NotBlank(groups = {saveCheck.class, updateCheck.class})
    private String itemName;

//    @NotNull(groups = {saveCheck.class, updateCheck.class})
//    @Range(min = 1000, max = 1000000, groups ={saveCheck.class, updateCheck.class} )
    private Integer price;

//    @NotNull(groups = {saveCheck.class, updateCheck.class})
//    @Max(value = 9999,groups = saveCheck.class)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
