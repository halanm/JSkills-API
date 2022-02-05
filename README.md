# JSkills

## Including JSkills

With Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.Rodriga0</groupId>
	<artifactId>JSkills</artifactId>
	<version>1.0</version>
    </dependency>
</dependencies>
```

With Gradle:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    compileOnly "com.github.Rodriga0:JSkills:1.0"
}
```

## How to Create new Quirks

### Creating Quirks

```Java
public class ExampleQuirk implements Quirk {
    @Override
    public String getName() {
        return "ExampleQuirk"; //The Quirk Name
    }

    @Override
    public Skill getSkill1() {
        return new Skill1(); //The Skill created in the example below
    }

    @Override
    public Skill getSkill2() {
        return new Skill2();
    }

    @Override
    public Skill getSkill3() {
        return new Skill3();
    }
}
```

### Creating Skills

```Java
public class ExampleSkill implements Skill {

    @Override
    public String getName() {
        return "ExampleSkill"; //The Skill Name
    }

    @Override
    public String getType() {
        return "SkillType"; //The Skill Type
    }

    @Override
    public void playEffect(Hero hero) {
        //Skill Code
    }
}
```

### Skill Types
There are 3 skill types:

EFFECT -> An normal skill with particle effect and damage.  
BUFF -> An skill wich will give a buff to hero like speed or health.  
COMMAND -> An skill wich will execute the list of commands in the config.yml.  

### Damage Event
To damage an entity with a skill use the custom Damage Event:

```Java
new SkillDamageEvent(hero, skill, entity);
```

P.S: it's not necessary to call the event

### Additional Values

Skill Buff intensity
```Java
double intensity = SkillProvider.getInstance().getValue(skill, hero);
```


Skill Buff duration
```Java
long duration = SkillProvider.getInstance().getDuration(skill, hero);
```

Skill Command List
```Java
List<String> commands = SkillProvider.getInstance().getCommands(skill, hero)
```

P.S: it's not necessary to execute the commands, this value is only useful if you need a specific command or the number of commands in the list.

Skill Damage
```Java
double damage = SkillProvider.getInstance().getDamage(skill, hero);
```

P.S: it's highly recommended that you don't damage the entity without the custom event.

## How to Register new Quirks


```Java
public class QuirkManager {

    private static QuirkManager quirkManager;
    private QuirkRepository quirkRepository;

    public QuirkManager() {
        quirkRepository = QuirkRepository.getInstance();
    }

    public static QuirkManager getInstance() {
        if (QuirkManager.quirkManager == null) {
            QuirkManager.quirkManager = new QuirkManager();
        }
        return QuirkManager.quirkManager;
    }

    public void registerQuirks(){
        quirkRepository.registerQuirk("Quirk1", new Quirk1());
        quirkRepository.registerQuirk("Quirk2", new Quirk2());
        quirkRepository.registerQuirk("Quirk3", new Quirk3());
    }
}
```

```Java
public void onEnable() {
    QuirkManager.getInstance().registerQuirks()
}
```
