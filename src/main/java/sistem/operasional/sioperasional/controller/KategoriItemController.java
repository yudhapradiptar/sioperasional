package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@Controller
public class KategoriItemController {
    @Autowired
    KategoriItemService kategoriItemService;

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/hardware-fulfillment/kategori/create", method = RequestMethod.GET)
    public String createKategoriFormPage(Model model) {
        KategoriItemModel newKategori = new KategoriItemModel();
        model.addAttribute("kategori", newKategori);

        return "form-create-kategori";
    }

    @RequestMapping(value = "/hardware-fulfillment/kategori", method = RequestMethod.POST)
    public String createKategoriSubmit(@ModelAttribute KategoriItemModel kategoriItem, Model model) {
        List<KategoriItemModel> listKategoriItem =  kategoriItemService.getKategoriItemList();
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

    @RequestMapping(value = "/hardware-fulfillment/kategori/all", method = RequestMethod.GET)
    public String viewAllKategori(Model model){
        List<KategoriItemModel> listAllKategoriItem = kategoriItemService.getKategoriItemList();
        model.addAttribute("listAllKategoriItem", listAllKategoriItem);

        return "list-kategori-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/kategori/delete")
    public String deleteKategoriItem(@RequestParam("idKategoriItem") Long idKategoriItem, Model model){
        KategoriItemModel deletedKategori = kategoriItemService.getKategoriItemByIdKategoriItem(idKategoriItem);
        model.addAttribute("kategori", deletedKategori);
        List<ItemModel> listItemKategori = itemService.getItemListByKategoriItem(kategoriItemService.getKategoriItemByIdKategoriItem(idKategoriItem));
        if(listItemKategori.size()!=0){
            return "fail-delete-kategori";
        }
        kategoriItemService.deleteKategoriItem(deletedKategori);
        return "success-delete-kategori";
    }
}
