package com.kh.myproduct.svc;

import com.kh.myproduct.dao.Product;
import com.kh.myproduct.dao.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSvcImpl implements ProductSvc{ //인터페이스의 구현체
  private final ProductDao productDao;

  @Override
  public Long save(Product product) {
    return productDao.save(product);
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productDao.findById(productId);
  }

  @Override
  public int update(Long productId, Product product) {
    return productDao.update(productId,product);
  }

  @Override
  public int delete(Long productId) {
    return productDao.delete(productId);
  }

  @Override
  public List<Product> findAll() {
    return productDao.findAll();
  }
}
