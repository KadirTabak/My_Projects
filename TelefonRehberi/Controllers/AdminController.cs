using TelefonRehberi.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;

namespace TelefonRehberi.Controllers
{
    public class AdminController : Controller
    {
        private readonly RehberContext context;

        public AdminController(RehberContext context){
            this.context = context;
        }
        public IActionResult Menu()
        {
            return View();
        }
         public IActionResult RehberEkle()
        {
            return View();
        }
        [HttpPost]
        public IActionResult RehberEkle(Rehber model)
        {
           
            context.Rehberler.Add(model);
            context.SaveChanges();
            return RedirectToAction("Rehber");
        }
        public IActionResult RehberSil(int id)
        {
            Rehber kayit=context.Rehberler.FirstOrDefault(x=>x.Id==id);
            context.Rehberler.Remove(kayit);     
            context.SaveChanges();
            return RedirectToAction("Rehber");
        }
        public IActionResult RehberGuncelle(int id)
        {
            Rehber kayit=context.Rehberler.FirstOrDefault(x=>x.Id==id);
            return View(kayit);
        }
        [HttpPost]
        public IActionResult RehberGuncelle(Rehber model)
        {
            context.Rehberler.Update(model);
            context.SaveChanges();
            return RedirectToAction("Rehber");
        }
        public IActionResult Rehber()
        {
            return View(context.Rehberler.ToList());
        }
    }
}
