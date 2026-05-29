package dk.sdu.mmmi.cbse.score;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private int score = 0;
    private int highscore = 0;

    @GetMapping
    public String Score(@RequestParam(value = "point") int point) {
        score += point;
        return String.valueOf(score);
    }

    @GetMapping("/highscore")
    public String getHighscore() {
        return String.valueOf(highscore);
    }

    @PostMapping("/submit")
    public String submit() {
        boolean isHighscore = score > highscore;
        if (isHighscore) {
            highscore = score;
        }
        score = 0;
        return String.valueOf(isHighscore);
    }

}

