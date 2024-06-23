using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using SQLITE.Models;

namespace SQLITE.Controllers;

public class HomeController : Controller
{
    private readonly AppDbContext context;

    public HomeController(AppDbContext context)
    {
        this.context = context;
    }

    public IActionResult Index()
    {
        var araba1 = context.arabalar.ToList();
        return View(araba1);
    }

    public IActionResult Sil(int id)
    {
        var araba = context.arabalar.FirstOrDefault(x => x.Id == id);
        context.arabalar.Remove(araba);
        context.SaveChanges();
        return RedirectToAction("Index");
    }

    public IActionResult Detay(int id)
    {
        var araba = context.arabalar.FirstOrDefault(x => x.Id == id);
        return View(araba);
    }
    public IActionResult Duzenle(int id)
    {
        var araba = context.arabalar.FirstOrDefault(x => x.Id == id);
        return View(araba);
    }

    [HttpPost]
    public IActionResult Duzenle(ArabaModel araba)
    {
        context.arabalar.Update(araba);
        context.SaveChanges();
        return RedirectToAction("Index");
    }

    public IActionResult Privacy()
    {
        return View();
    }

    public IActionResult Ekle()
    {
        return View();
    }

    [HttpPost]
    public IActionResult Ekle(ArabaModel araba)
    {
        context.Add(araba);
        context.SaveChanges();
        return RedirectToAction("Index");
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error()
    {
        return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
    }
}
