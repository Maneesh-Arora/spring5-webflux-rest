package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.CategoryRepository;
import guru.springframework.spring5webfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block() == 0)
        {
            loadCategories();
        }
        if(vendorRepository.count().block() == 0)
        {
            loadVendors();
        }
    }

    private void loadVendors() {

        vendorRepository.save(Vendor.builder().id("1").firstName("Maneesh").lastName("Arora").build()).block();
        vendorRepository.save(Vendor.builder().id("2").firstName("M").lastName("A").build()).block();
        vendorRepository.save(Vendor.builder().id("3").firstName("Jimmy").lastName("Weston").build()).block();
        vendorRepository.save(Vendor.builder().id("4").firstName("Jessie").lastName("Waters").build()).block();



        System.out.println("Vendors Loaded. Count =" + vendorRepository.count().block());
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().id("1").description("Fruits").build()).block();
        categoryRepository.save(Category.builder().id("2").description("Nuts").build()).block();
        categoryRepository.save(Category.builder().id("3").description("Breads").build()).block();
        categoryRepository.save(Category.builder().id("4").description("Meats").build()).block();
        categoryRepository.save(Category.builder().id("5").description("Eggs").build()).block();

        System.out.println("Categories Loaded. Count = " + categoryRepository.count().block());


    }
}
