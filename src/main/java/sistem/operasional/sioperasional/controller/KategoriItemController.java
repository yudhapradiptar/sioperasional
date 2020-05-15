package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.KategoriItemService;
import sistem.operasional.sioperasional.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/hardware-fulfillment/kategori")
public class KategoriItemController {
    @Autowired
    KategoriItemService kategoriItemService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createKategoriFormPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        KategoriItemModel newKategori = new KategoriItemModel();
        model.addAttribute("kategori", newKategori);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());


        return "form-create-kategori";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createKategoriSubmit(@ModelAttribute KategoriItemModel kategoriItem, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        List<KategoriItemModel> listKategoriItem =  kategoriItemService.getKategoriItemList();
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        try {
            for(KategoriItemModel kategori : listKategoriItem){
                if(kategoriItem.getNamaKategoriItem().equals(kategori.getNamaKategoriItem())){
                    model.addAttribute("kategori",kategoriItem);
                    return "fail-create-kategori";
                }
            }
            kategoriItemService.createKategoriItem(kategoriItem);
            model.addAttribute("kategori",kategoriItem);
            return "success-create-kategori";
        } catch (NullPointerException e) {
            return "form-create-kategori";
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String viewAllKategori(@AuthenticationPrincipal UserDetails currentUser, Model model){
        List<KategoriItemModel> listAllKategoriItem = kategoriItemService.getKategoriItemList();
        model.addAttribute("listAllKategoriItem", listAllKategoriItem);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "list-kategori-item";
    }

    @RequestMapping(value = "/delete")
    public String deleteKategoriItem(@RequestParam("idKategoriItem") Long idKategoriItem, @AuthenticationPrincipal UserDetails currentUser, Model model){
        KategoriItemModel deletedKategori = kategoriItemService.getKategoriItemByIdKategoriItem(idKategoriItem);
        model.addAttribute("kategori", deletedKategori);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        List<ItemModel> listItemKategori = itemService.getItemListByKategoriItem(kategoriItemService.getKategoriItemByIdKategoriItem(idKategoriItem));
        if(listItemKategori.size()!=0){
            return "fail-delete-kategori";
        }
        kategoriItemService.deleteKategoriItem(deletedKategori);
        return "success-delete-kategori";
    }
}