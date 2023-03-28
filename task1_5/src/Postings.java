public class Postings {
    @Entity
    @Table(name = "postings")
    public class Postings {
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

        // getters and setters
    }
}
