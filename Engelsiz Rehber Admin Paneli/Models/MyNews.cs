using Google.Cloud.Firestore;

namespace Engelsiz_Rehber_Admin_Paneli.Models
{

    [FirestoreData]
    public class MyNews
    {
        public string? id { get; set; }
    
        [FirestoreProperty]
        public string? content { get; set; }
   
    
        [FirestoreProperty]
        public string? date { get; set; }
    
    }
   
}
