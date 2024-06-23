using Microsoft.EntityFrameworkCore;

public class AppDbContext: DbContext {
    public AppDbContext() { }
    public AppDbContext(DbContextOptions<AppDbContext> options) : base (options) { }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlite("Data Source = arabalar.db");
    }

    public DbSet<ArabaModel> arabalar { get; set; }
}