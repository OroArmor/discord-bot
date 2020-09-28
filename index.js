const Discord = require('discord.js');
const client = new Discord.Client();
const fs = require("fs");

const commands = require(`./commands/commands.js`);

let commandPrefix = {
  prefix: "!"
};

fs.readFile('./configs/config.txt', (err, file) => {
  if (err) {
      fs.writeFile('./configs/config.txt', `prefix: ${commandPrefix.prefix}`, (err, data) =>{});
    return;
  }
  let data = file.toString('utf8').split(/\n/);
  for(let line in data){
    if(data[line].startsWith("prefix: ")){
      commandPrefix.prefix = data[line].trim().split(/\s+/)[1];
    }
  }
});


client.on('ready', () => {
  console.log(`Logged in as ${client.user.tag}!`);
});

client.on('message', msg => {
  if (!msg.content.startsWith(commandPrefix.prefix)) {
    if (msg.content.toLowerCase().includes("forge") && !msg.author.bot) {
      msg.channel.send("Eww... Forge is bad!")
    }
    return;
  }

  let tokens = msg.content.split(/\s+/);
  let commandName = tokens[0].substring(1);

  if (commandName === `change-prefix` && msg.member.permissions.has(Discord.Permissions.ADMINISTRATOR)) {
    msg.reply(`Changing command prefix to ${tokens[1][0]}`)
    commandPrefix.prefix = tokens[1][0];
    fs.writeFile('./configs/config.txt', `prefix: ${commandPrefix.prefix}`, (err, data) =>{});
  } else if (commandName === `commands`) {
    let message = "Available commands are: ```";
    for (let commandValue in commands) {
      let command = commands[commandValue];
      if (command.permissions(msg.member, msg.channel))
        message += `\n${commandPrefix.prefix}${command.name}: ${command.desc}`
    }
    msg.channel.send(message + "```");
  } else {
    for (let command in commands) {
      if (commands[command].name === commandName) {
        commands[command].runs(msg.member, msg.channel, tokens);
        return;
      }
    }
    msg.channel.send("Command not known");
  }
});

client.on("guildMemberAdd", member => {
  let role = member.guild.roles.cache.find(r => r.name === 'Mod Lover')
  member.roles.add(role);
})

client.login(`${process.env.BOT_TOKEN}`);
