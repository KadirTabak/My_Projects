using Microsoft.EntityFrameworkCore;

namespace TelefonRehberi.Models {
    public class RehberContext : DbContext {
        public RehberContext() { }
        public RehberContext(DbContextOptions<RehberContext> options) : base (options) { }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite("Data Source=RehberDB.db");
        }

        public DbSet <Rehber> Rehberler { get; set; }
    }
}