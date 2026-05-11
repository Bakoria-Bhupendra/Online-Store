package com.bobby.onlinestore.entities;

@lombok.Getter
@lombok.Setter@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "carts")
public class Cart {
@jakarta.persistence.Id
@jakarta.validation.constraints.Size(max = 16)
@jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
@jakarta.persistence.Column(name = "id", nullable = false, length = 16)
private java.util.UUID id;

@jakarta.validation.constraints.NotNull
@org.hibernate.annotations.ColumnDefault("(curdate())")
@jakarta.persistence.Column(name = "date_created", nullable = false)
private java.time.LocalDate dateCreated;

@jakarta.persistence.OneToMany(mappedBy = "cart")
private java.util.Set<com.bobby.onlinestore.entities.CartItem> cartItems = new java.util.LinkedHashSet<>();



}