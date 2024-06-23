using Google.Cloud.Firestore;

namespace Engelsiz_Rehber_Admin_Paneli.Models
{
    [FirestoreData]
    public class Users
    {
        public string? id { get; set; }

        [FirestoreProperty]
        public string? email { get; set; }
        
        [FirestoreProperty]
        public string? name { get; set; }
        
        [FirestoreProperty]
        public string? type { get; set; }
    }
}
