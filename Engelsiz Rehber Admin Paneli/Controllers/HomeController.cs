using Engelsiz_Rehber_Admin_Paneli.Models;
using Google.Cloud.Firestore;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Text;

namespace Engelsiz_Rehber_Admin_Paneli.Controllers
{
    public class HomeController : Controller
    {
        private readonly string _firebaseServerKey = "AAAAmKp75Vo:APA91bFnCyXPAang-vma2_B15aOccIXRBoo_SANr6mANuaYgU9rJWkx3r_4GM7jRVIZhCUDP-4Q14r-hGQIy9DMUL55WNXs2e4Mynsbrtb8PztSXVEgBiPds4OSIrWaWDALaFnbtFePe";
        private readonly string _firebaseSenderId = "655695275354";
        string path = "C:\\Users\\HP\\Desktop\\Engelsiz Rehber Admin Paneli\\wwwroot\\engelsiz-r-firebase-adminsdk-sk2jg-626a9eb096.json";
        private string projectId;
        FirestoreDb db;

        public HomeController()
        {
            Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
            projectId = "engelsiz-r";
            db = FirestoreDb.Create(projectId);
        }

        public async Task<IActionResult> DeleteEvent(String id)
        {
            DocumentReference documentReference = db.Collection("events").Document(id);
            await documentReference.DeleteAsync();
            return RedirectToAction("Index");
        }
        public async Task<IActionResult> DeleteNews(String id)
        {
            DocumentReference documentReference = db.Collection("news").Document(id);
            await documentReference.DeleteAsync();
            return RedirectToAction("News");
        }
        public async Task<IActionResult> DeleteUser(String id)
        {
            DocumentReference documentReference = db.Collection("users").Document(id);
            await documentReference.DeleteAsync();
            return RedirectToAction("Users");
        }


        public IActionResult Messaging()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Messaging(string Nofititle, string Nofimessage)
        {

            var notification = new
            {
                to = "/topics/genel", // Tüm cihazların abone olduğu konu
                notification = new
                {
                    title = Nofititle,
                    body = Nofimessage
                }
            };

            var json = JsonConvert.SerializeObject(notification);
            var httpRequest = new HttpRequestMessage(HttpMethod.Post, "https://fcm.googleapis.com/fcm/send");
            httpRequest.Headers.TryAddWithoutValidation("Authorization", $"key={_firebaseServerKey}");
            httpRequest.Content = new StringContent(json, Encoding.UTF8, "application/json");

            using (var httpClient = new HttpClient())
            {
                var response = await httpClient.SendAsync(httpRequest);
                if (response.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
                else
                {
                    var error = await response.Content.ReadAsStringAsync();
                    return Content($"Bildirim Gönderilemedi Hata: {error}");
                }
            }

        }

        public IActionResult AddNews() { return View(); }
        public async Task<IActionResult> Index()
        {
            Query query = db.Collection("events");
            QuerySnapshot querySnapshot = await query.GetSnapshotAsync();
            List<Event> events = new List<Event>();
            foreach (DocumentSnapshot document in querySnapshot.Documents)
            {
                if (document.Exists)
                {
                    Dictionary<string, object> keys = document.ToDictionary();
                    string json = JsonConvert.SerializeObject(keys);
                    Event addEvent = JsonConvert.DeserializeObject<Event>(json);
                    addEvent.id = document.Id;
                    events.Add(addEvent);
                }
            }
            return View(events);
        }

        public async Task<IActionResult> Users()
        {

            Query query = db.Collection("users");
            QuerySnapshot querySnapshot = await query.GetSnapshotAsync();
            List<Users> user = new List<Users>();
            foreach (DocumentSnapshot document in querySnapshot.Documents)
            {
                if (document.Exists)
                {
                    Dictionary<string, object> keys = document.ToDictionary();
                    string json = JsonConvert.SerializeObject(keys);
                    Users addUsers = JsonConvert.DeserializeObject<Users>(json);
                    addUsers.id = document.Id;
                    user.Add(addUsers);
                }
            }
            return View(user);

        }

        public IActionResult Reload()
        { 
            return RedirectToAction("Index");
        }

        public IActionResult ReloadNews()
        {
            return RedirectToAction("News");
        }

        public IActionResult AddEvent()
        {
            return View();
        }

       

        [HttpPost]
        public async Task<ActionResult> AddEvent(Event model)
        {
            CollectionReference collectionReference = db.Collection("events");
            await collectionReference.AddAsync(model);
            return RedirectToAction(nameof(Index));
        }

        [HttpPost]
        public async Task<ActionResult> AddNews(MyNews model)
        {
            CollectionReference collectionReference = db.Collection("news");
            await collectionReference.AddAsync(model);
            return RedirectToAction(nameof(News));
        }

        [HttpGet]
        public async Task<IActionResult> updateEvent(String id)
        {
            DocumentReference documentReference = db.Collection("events").Document(id);
            DocumentSnapshot documentSnapshot = await documentReference.GetSnapshotAsync();
            if (documentSnapshot.Exists)
            {
                Event updateevents = documentSnapshot.ConvertTo<Event>();
                return View(updateevents);
            }
            return NotFound();
        }

        [HttpPost]
        public async Task<IActionResult> updateEvent(Event model)
        {
            DocumentReference documentReference = db.Collection("events").Document(model.id);
            await documentReference.SetAsync(model, SetOptions.Overwrite);
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> News()
        {
            Query query = db.Collection("news");
            QuerySnapshot querySnapshot = await query.GetSnapshotAsync();
            List<MyNews> news = new List<MyNews>();
            foreach (DocumentSnapshot document in querySnapshot.Documents)
            {
                if (document.Exists)
                {
                    Dictionary<string, object> keys = document.ToDictionary();
                    string json = JsonConvert.SerializeObject(keys);
                    MyNews addNews = JsonConvert.DeserializeObject<MyNews>(json);
                    addNews.id = document.Id;
                    news.Add(addNews);
                }
            }
            return View(news);
        }

    }
}
