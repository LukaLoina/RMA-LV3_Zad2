package hr.ferit.lloina.LV3_Zad2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import hr.ferit.lloina.LV3_Zad2.adapters.ViewPagerAdapter
import hr.ferit.lloina.LV3_Zad2.databinding.ActivityMainBinding
import hr.ferit.lloina.LV3_Zad2.fragments.InspiringPersonsListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        createPeopleList()

        viewPager = mainBinding.vpViewPager

        val event = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 0) InspiringPersonInputFragment.clearPerson()
            }
        }

        viewPager.registerOnPageChangeCallback(event)
        val screens = listOf<() -> Fragment>({ InspiringPersonsListFragment.create()}, { InspiringPersonInputFragment.create()})
        viewPager.adapter = ViewPagerAdapter(this, screens)
    }
    companion object{
        lateinit var viewPager : ViewPager2
        fun setPage(pageNumber : Int){
            viewPager.currentItem = pageNumber
        }
    }

    private fun createPeopleList(){
        //val list = PeopleRepository.inspiringPersonList
        /*
        list.add(InspiringPerson("Alonzo Church",
                "Alonzo Church was an American mathematician and logician who made major contributions to mathematical logic and the foundations of theoretical computer science. He is best known for the lambda calculus, Church–Turing thesis, proving the unsolvability of the Entscheidungsproblem, Frege–Church ontology, and the Church–Rosser theorem.",
                "14.6.1903",
                "11.7.1995",
                mutableListOf("Well it was not exactly a dissertation in logic, at least not the kind of logic you would find in Whitehead and Russell’s Principia Mathematica for instance. It looked more like mathematics; no formalized language was used.",
                "The only thing that might have annoyed some mathematicians was the presumption of assuming that maybe the axiom of choice could fail, and that we should look into contrary assumptions."),
                BitmapFactory.decodeResource(resources, R.drawable.alonzo_church)))
        list.add(InspiringPerson("John Von Neumann",
                "Von Neumann was a child prodigy, born into a banking family in Budapest, Hungary. When only 6 years old he could divide eight-digit numbers in his head.",
                "28.10.1903",
                "8.2.1957",
                mutableListOf("If people do not believe that mathematics is simple, it is only because they do not realize how complicated life is.",
                              "Anyone who considers arithmetical methods of producing random numbers is, of course, in a state of sin." ),
                BitmapFactory.decodeResource(resources, R.drawable.john_von_neumann)))
        list.add(InspiringPerson(
                "Edsger Dijkstra",
                "One of the most influential figures of computing science's founding generation, Dijkstra helped shape the new discipline both as an engineer and a theorist.",
                "11.5.1930",
                "6.8.2002",
                mutableListOf(
                        "Program testing can be used to show the presence of bugs, but never to show their absence.",
                        "The question of whether computers can think is just like the question of whether submarines can swim."
                ),
                BitmapFactory.decodeResource(resources, R.drawable.edsger_wybe_dijkstra)
        ))
        list.add(InspiringPerson("Richard Stallman",
                "Richard Stallman is American free software movement activist and programmer. He campaigns for software to be distributed in a manner such that its users receive the freedoms to use, study, distribute, and modify that software. Software that ensures these freedoms is termed free software.",
                "16.3.1953",
                "",
                mutableListOf("I consider that the golden rule requires that if I like a program I must share it with other people who like it.",
                "A hacker is someone who enjoys playful cleverness — not necessarily with computers.",
                "The use of “hacker” to mean “security breaker” is a confusion on the part of the mass media. We hackers refuse to recognize that meaning, and continue using the word to mean someone who loves to program, someone who enjoys playful cleverness, or the combination of the two."
                ),
                BitmapFactory.decodeResource(resources, R.drawable.richard_stallman)))
        list.add(InspiringPerson("Linus Torvalds",
                "Linus Torvalds is a Finnish-American software engineer who is the creator and, historically, the main developer of the Linux kernel, used by Linux distributions and other operating systems such as Android and Chrome OS. He also created the distributed revision control system Git and the scuba dive logging and planning software Subsurface. ",
                "28 December 1969",
                "",
                mutableListOf("I'm doing a(n) (free) operating system (just a hobby, won't be big and professional like gnu) for 386(486) AT clones.",
                "Talk is cheap. Show me the code.",
                "Those that can, do. Those that can't, complain.",
                "Every time I see some piece of medical research saying that caffeine is good for you, I high-five myself. Because I'm going to live forever. "),
                BitmapFactory.decodeResource(resources, R.drawable.linus_torvalds)))
        //list.add(InspiringPerson("","","", "", mutableListOf(),  BitmapFactory.decodeResource(resources, R.drawable.unknown_person)))
        */
    }
}



