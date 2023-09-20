package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.CartDto;
import com.example.shoppingmallServer.Entity.Cart;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.EmptyValueException;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.example.shoppingmallServer.Repository.CartRepository;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Response.CartResponse;
import com.example.shoppingmallServer.Response.ImageResponse;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    @Transactional
    public ResponseEntity<String> cartInsert(CartDto cartDto) {
        Member findMember = memberRepository.findOneById(cartDto.getMemberKey());

        if (findMember == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }
        Item findItem = itemRepository.findOneById(cartDto.getItemKey());
        if (findItem == null) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        Cart cart = Cart.createCart(findMember, findItem, cartDto);
        if (cart == null) {
            throw new EmptyValueException("잘못된 입력이 존재합니다.");
        }

        return cartRepository.cartInsert(cart);
    }

    @Transactional
    public ResponseEntity<List<CartResponse>> findOneById(int memberKey) throws IOException {
        List<Tuple> findByCart = cartRepository.findOneById(memberKey);

        if (findByCart == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        List<CartResponse> cartResponses = new ArrayList<>();

        for (Tuple cart: findByCart) {
            byte[] image = getImageDataFromPath(cart.get(4, String.class));
            cartResponses.add(new CartResponse(cart, image));
        }
        return new ResponseEntity<>(cartResponses, HttpStatus.OK);
    }
    public byte[] getImageDataFromPath(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        Resource imageResource = new FileSystemResource((path.toFile()));

        if (imageResource.exists()) {
            return imageResource.getContentAsByteArray();
        } else {
            throw new FileNotFoundException("상품 이미지가 존재하지 않습니다.");
        }
    }

    @Transactional
    public ResponseEntity<String> remove(int cartKey, int memberKey) {
        Cart cartById = cartRepository.findCartById(cartKey, memberKey);
        if (cartById == null) {
            throw new NotFoundException("장바구니 정보가 존재하지 않습니다.");
        }
        return cartRepository.remove(cartById);
    }
}

