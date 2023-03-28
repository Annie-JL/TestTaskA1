package controller;

import model.Postings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.PostingsRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/postings")
public class PostingsController {
    private final PostingsRepository postingsRepository;

    public PostingsController(PostingsRepository postingsRepository) {
        this.postingsRepository = postingsRepository;
    }

    @GetMapping
    public List<Postings> getPostingsByPeriodAndAuthorizedDel(@RequestParam String period, @RequestParam(required = false) Boolean authorizedDel) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        switch (period) {
            case "day":
                startDate = endDate.minusDays(1);
                break;
            case "month":
                startDate = endDate.minusMonths(1);
                break;
            case "quarter":
                startDate = endDate.minusMonths(3);
                break;
            case "year":
                startDate = endDate.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        return postingsRepository.findPostingsByPeriodAndAuthorizedDel(startDate, endDate, authorizedDel);
    }
}
