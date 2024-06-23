using Google.Cloud.Firestore;

namespace Engelsiz_Rehber_Admin_Paneli.Models
{
    [FirestoreData]
    public class Event
    {
        public string? id { get; set; }

        [FirestoreProperty]
        public string? title{ get; set; }
        
        [FirestoreProperty]
        public string? longtext { get; set; }
        
        [FirestoreProperty]
        public string? startDate { get; set; }
        
        [FirestoreProperty]
        public string? finishDate { get; set; }
        
        [FirestoreProperty]
        public string? eventLocal { get; set; }
        
    }
}
