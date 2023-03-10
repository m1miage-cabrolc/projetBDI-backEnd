package miagiles.gromed.service;

import lombok.Builder;
import lombok.extern.java.Log;
import miagiles.gromed.entity.Commande;
import miagiles.gromed.entity.Presentation;
import miagiles.gromed.entity.PresentationDeCommande;
import miagiles.gromed.entity.Utilisateur;
import miagiles.gromed.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
@Builder

public class CommandeService {

    @Autowired
    PresentationDeCommandeRepository presentationDeCommandeRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PresentationService presentationService;

    @Autowired
    PresentationRepository presentationRepository;

    @Autowired
    PresentationCommandeRepository presentationCommandeRepository;

    public Iterable<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public void createPanier(String userMail) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(userMail);
            Commande commande = new Commande();
            commande.setEtatCommande("panier");
            commande.setCommandeType(false);
            commandeRepository.save(commande);
            utilisateur.addCommande(commande);
            utilisateurRepository.save(utilisateur);

        } catch (Exception e) {
            log.info("Creation failed");
        }

    }

    public Commande getPanier(String userMail) {
        Utilisateur user = utilisateurRepository.findByAdresseMail(userMail);
        Commande panier = null;
        for (Commande cmd : user.getCommandes()) {
            if (cmd.getEtatCommande().equals("panier")) {
                panier = cmd;
            }
        }
        return panier;
    }

    public List<Integer> validerCommande(String userMail, boolean isForced) {
        List<Integer> idPresentationManquante;
        idPresentationManquante = new ArrayList<>();
        boolean isOnHold = false;
        Commande panier = getPanier(userMail);

        if (panier == null) {
            return new ArrayList<>();
        }

        List<PresentationDeCommande> listPresentationCommande = presentationCommandeRepository.findByPresentationCommandeCommande(panier.getNumeroCommande());

        if (!isForced) {
            for (PresentationDeCommande p : listPresentationCommande) {
                Presentation presentation = presentationService.getPresentationByCIP7(p.getPresentationCommande().getPresentation());
                if (presentation.getStockLogique() < p.getQuantite()) {
                    isOnHold = true;
                    idPresentationManquante.add((int) presentation.getCodeCIP7());
                }
            }
        }

        if (isOnHold) {
            return idPresentationManquante;
        }

        for (PresentationDeCommande p : listPresentationCommande) {
            Presentation presentation = presentationService.getPresentationByCIP7(p.getPresentationCommande().getPresentation());
            presentation.setStockLogique(presentation.getStockLogique() - p.getQuantite());
            presentationRepository.save(presentation);
            presentationDeCommandeRepository.save(p);
        }

        panier.setEtatCommande("en cours");
        commandeRepository.save(panier);

        return idPresentationManquante;
    }
}
