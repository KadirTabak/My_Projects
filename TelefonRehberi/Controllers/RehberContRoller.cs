using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TelefonRehberi.Models;

namespace TelefonRehberi.Controllers
{
  public class RehberController : Controller
  {   
    private readonly RehberContext context;
    public RehberController(RehberContext context)
    {
      this.context = context;
    }

    public IActionResult Kayitlar()
    {
      return View(context.Rehberler.ToList());
    }

    public IActionResult Detay(int id)
    {
      Rehber rehber = context.Rehberler.FirstOrDefault(x=>x.Id==id);
      return View(rehber);
    }
  }
}