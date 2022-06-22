package guru.springframework.spring5webfluxrest.controller;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.CategoryRepository;
import guru.springframework.spring5webfluxrest.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().id("1").firstName("Cat1").build(),
                        Vendor.builder().id("2").firstName("Cat2").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById("anyid"))
                .willReturn(Mono.just(Vendor.builder().id("1").firstName("Cat1").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/anyid")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void createTest() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().id("1").firstName("Ven1").build()));

        Mono<Vendor> vendor = Mono.just(Vendor.builder().id("1").build());
        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendor,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateTest() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().id("1").firstName("Ven1").build()));

        Mono<Vendor> vendor = Mono.just(Vendor.builder().id("1").build());
        webTestClient.put()
                .uri("/api/v1/vendors/1")
                .body(vendor,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patchVendors() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().id("1").firstName("Cat1").build()));

        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().id("1").firstName("Cat1").build()));

        Mono<Vendor> vendor = Mono.just(Vendor.builder().firstName("Some Cat").build());

        webTestClient.patch().uri("/api/v1/vendors/1")
                .body(vendor,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}