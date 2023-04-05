package hello.itemService.domain.item;

import lombok.Data;

@Data   //사용에 주의

public class Item {

    private long id;            //아이디
    private String itemName;    //이름
    private Integer quantity;   //수량
    private Integer price;  //가격

    public  Item(){
    }

    public Item(String itemName, Integer quantity, Integer price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
