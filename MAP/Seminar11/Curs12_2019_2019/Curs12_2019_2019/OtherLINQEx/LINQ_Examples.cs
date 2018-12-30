using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.OtherLINQEx
{
    public class Car
    {
        public string Make { get; set; }
        public string Model { get; set; }
        public string Color { get; set; }

        public override string ToString()
        {
            return Make + " " + Model + " " + Color;
        }
    }
    class LINQ_Examples
    {
        public static void GroupByExample()
        {
            //------------------------
            List<Car> cars = new List<Car>();
            cars.Add(new Car { Make = "Honda", Model = "Accord", Color = "blue" });
            cars.Add(new Car { Make = "Dodge", Model = "Caravan", Color = "green" });
            cars.Add(new Car { Make = "Ford", Model = "Crown Victoria", Color = "red" });
            cars.Add(new Car { Make = "Honda", Model = "Civic", Color = "blue" });
            cars.Add(new Car { Make = "Dodge", Model = "Stratus", Color = "blue" });
            cars.Add(new Car { Make = "Honda", Model = "Pilot", Color = "red" });

            IEnumerable<IGrouping<string, Car>> carGroups = cars.GroupBy(c => c.Make);
            //-----------------

            foreach (IGrouping<string, Car> g in carGroups)
            {
                Console.WriteLine(g.Key);
                foreach (Car c in g)
                    Console.WriteLine(c);


            }
        }
    }
}
