package bts.KCamps.controllers.rest;

import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.UserRepo;
import bts.KCamps.model.Camp;
import bts.KCamps.service.impl.CampServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/camp")
public class CampRestController {

    private final CampRepo campRepo;
    private final UserRepo userRepo;
    private final CampServiceImpl campService;

    public CampRestController(CampRepo campRepo, UserRepo userRepo, CampServiceImpl campService) {
        this.campRepo = campRepo;
        this.userRepo = userRepo;
        this.campService = campService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Camp>> getAllCamps() {
        Iterable<Camp> all = campRepo.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("{id}")
    public ResponseEntity<Camp> getById(@PathVariable Long id) {
        return campRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    @CrossOrigin("localhost:8080")
    public ResponseEntity<Camp> addCamp(@RequestBody Camp camp) {
        Camp saved = campRepo.save(camp);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("{id}")
    @CrossOrigin("localhost:8080")
    public ResponseEntity<Camp> updateCamp(@PathVariable Long id, @RequestBody Camp camp) {
        Optional<Camp> fromDb = campRepo.findById(id);
        if (fromDb.isPresent()) {
            Camp target = fromDb.get();
            BeanUtils.copyProperties(camp, target, "id");
            campRepo.save(target);
            return ResponseEntity.ok(target);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @CrossOrigin("localhost:8080")
    public ResponseEntity<HttpStatus> deleteCamp(@PathVariable Long id) {
        campRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
