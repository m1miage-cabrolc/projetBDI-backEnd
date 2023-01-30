package miagiles.gromed.service;

import lombok.extern.java.Log;
import miagiles.gromed.entity.Commande;
import miagiles.gromed.entity.Utilisateur;
import miagiles.gromed.repository.CommandeRepository;
import miagiles.gromed.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



//@Service
//s@Log
public class CommandeService {
    @Autowired

    CommandeRepoitory commandeRepoitory;
    
    CommandeRepository commandeRepository;
    @Autowired
    UtilisateurService utilisateurService;

    public void createCommande(Commande commande ) {
        try{

            commandeRepoitory.save(commande);

        }catch(Exception e){
            log.info("Creation failed");
        }

     }


    public Commande getPanier(String userMail) {
        Utilisateur user = utilisateurRepository.findByAdresseMail(userMail);
        Commande panier =null ;
        for(Commande cmd : user.getCommandes()){
            if(cmd.getEtatCommande().equals("Panier")){
                panier=cmd;
            }
        }
        return panier;
    }


}
