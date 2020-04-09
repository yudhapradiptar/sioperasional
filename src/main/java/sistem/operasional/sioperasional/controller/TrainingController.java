package sistem.operasional.sioperasional.controller;

import sistem.operasional.sioperasional.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    TrainingService trainingService;



}