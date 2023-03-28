package model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "postings")
public class Postings {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matDoc")
    private Long matDoc;

    @Column(name = "item")
    private String item;

    @Column(name = "docDate")
    private LocalDate docDate;

    @Column(name = "pstngDate")
    private LocalDate pstngDate;

    @Column(name = "matDescription")
    private String matDescription;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "BUn")
    private String BUn;

    @Column(name = "amountLC")
    private Double amountLC;

    @Column(name = "Crcy")
    private String Crcy;

    @Column(name = "userName")
    private String userName;

    @Column(name = "authorizedDel")
    private Boolean authorizedDel;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getMatDoc() {
        return matDoc;
    }

    public void setMatDoc(Long matDoc) {
        this.matDoc = matDoc;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public LocalDate getPstngDate() {
        return pstngDate;
    }

    public void setPstngDate(LocalDate pstngDate) {
        this.pstngDate = pstngDate;
    }

    public String getMatDescription() {
        return matDescription;
    }

    public void setMatDescription(String matDescription) {
        this.matDescription = matDescription;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBUn() {
        return BUn;
    }

    public void setBUn(String BUn) {
        this.BUn = BUn;
    }

    public Double getAmountLC() {
        return amountLC;
    }

    public void setAmountLC(Double amountLC) {
        this.amountLC = amountLC;
    }

    public String getCrcy() {
        return Crcy;
    }

    public void setCrcy(String crcy) {
        Crcy = crcy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getAuthorizedDel() {
        return authorizedDel;
    }

    public void setAuthorizedDel(Boolean authorizedDel) {
        this.authorizedDel = authorizedDel;
    }

    // getters and setters
}
